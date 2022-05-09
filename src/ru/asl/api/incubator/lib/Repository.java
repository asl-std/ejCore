package ru.asl.api.incubator.lib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import com.google.common.io.ByteStreams;

import ru.asl.api.incubator.lib.dependency.Dependency;
import ru.asl.api.incubator.lib.exception.DownloadException;

public enum Repository {
	MAVEN_CENTRAL("https://repo1.maven.org/maven2/");

	private final String url;

	Repository(String url) {
		this.url = url;
	}

	protected URLConnection openConnection(Dependency dependency) throws IOException {
		final URL dependencyUrl = new URL(url + dependency.getMavenRepoPath());
		return dependencyUrl.openConnection();
	}

	public byte[] downloadRaw(Dependency dependency) throws DownloadException {
		try {
			final URLConnection connection = openConnection(dependency);
			final InputStream in = connection.getInputStream();
			try {
				final byte[] bytes = ByteStreams.toByteArray(in);
				if (bytes.length == 0)
					throw new DownloadException("Empty stream");
				final byte[] arrayOfByte1 = bytes;
				if (in != null)
					in.close();
				return arrayOfByte1;
			} catch (final Throwable throwable) {
				if (in != null)
					try {
						in.close();
					} catch (final Throwable throwable1) {
						throwable.addSuppressed(throwable1);
					}
				throw throwable;
			}
		} catch (final Exception e) {
			throw new DownloadException(e);
		}
	}

	public byte[] download(Dependency dependency) throws DownloadException {
		final byte[] bytes = downloadRaw(dependency);
		final byte[] hash = Dependency.createDigest().digest(bytes);
		if (!dependency.checksumMatches(hash))
			throw new DownloadException("Downloaded file had an invalid hash. Expected: "
					+ Base64.getEncoder().encodeToString(dependency.getChecksum()) + " Actual: "
					+ Base64.getEncoder().encodeToString(hash));
		return bytes;
	}

	public void download(Dependency dependency, Path file) throws DownloadException {
		try {
			Files.write(file, download(dependency));
		} catch (final IOException e) {
			throw new DownloadException(e);
		}
	}

}
