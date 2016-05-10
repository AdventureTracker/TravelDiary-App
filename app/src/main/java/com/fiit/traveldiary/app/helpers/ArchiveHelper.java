package com.fiit.traveldiary.app.helpers;

import android.net.Uri;
import android.util.Log;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Jakub Dubec on 10/05/16.
 */
public final class ArchiveHelper {

	private static final int BUFFER = 1024;

	/**
	 *
	 * @param files Filename without extension
	 * @param archiveFile Existing file
	 * @return Is operation successful?
	 */
	public static boolean zip(Map<String, Uri> files, File archiveFile) {

		try {
			BufferedInputStream origin;
			FileOutputStream destination = new FileOutputStream(archiveFile);
			ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(destination));
			byte data[] = new byte[BUFFER];

			for (Map.Entry<String, Uri> entry : files.entrySet()) {

				Log.w("Compress", String.format("Adding %s", entry.getValue()));

				FileInputStream fileInputStream = new FileInputStream(entry.getValue().getPath());

				origin = new BufferedInputStream(fileInputStream, BUFFER);

				ZipEntry zipEntry = new ZipEntry(entry.getKey() + "." + entry.getValue().getPath().substring(entry.getValue().getPath().lastIndexOf(".") + 1));
				zipOutputStream.putNextEntry(zipEntry);

				int count;

				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					zipOutputStream.write(data, 0, count);
				}

				origin.close();

			}

			zipOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
