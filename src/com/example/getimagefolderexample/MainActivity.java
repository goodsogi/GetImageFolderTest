package com.example.getimagefolderexample;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ArrayList<File> imageFolders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageFolders = new ArrayList<File>();

		// 외부저장장치 루트 경로 파일 가져옴
		File rootFile = Environment.getExternalStorageDirectory();

		// 이미지를 담고 있는 폴더를 추출해서 imageFolders에 저장
		getFiles(rootFile);
		// 이미지 폴더의 이름 출력
		TextView imageFolderName = (TextView) findViewById(R.id.image_folder_name);
		StringBuilder name = new StringBuilder();
		for (int i = 0; i < imageFolders.size(); i++) {
			name.append(imageFolders.get(i).getName());
			name.append("\n");
		}
		imageFolderName.setText(name);
	}

	/**
	 * 이미지를 담고 있는 폴더 추출
	 * 
	 * @param file
	 * @return
	 */
	private void getFiles(File file) {
		File[] files = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				File subFile = new File(dir, filename);
				// 디렉토리 파일인 경우 재귀호출, 히든 파일은 제외시킴
				if (!subFile.isHidden() && subFile.isDirectory())
					getFiles(subFile);
				// 이미지파일인 경우만 추출, .으로 시작하는 파일 제외
				return ((filename.contains(".png") || filename.contains(".jpg")) && !filename
						.startsWith("."));
			}
		});
		// 이미지 폴더에 추가
		for (File resultFile : files) {

			if (!imageFolders.contains(resultFile.getParentFile()))
				imageFolders.add(resultFile.getParentFile());
		}

	}

}
