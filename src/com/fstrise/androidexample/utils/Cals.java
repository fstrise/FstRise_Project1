package com.fstrise.androidexample.utils;

public class Cals {
	public static int w5;
	public static int w10;
	public static int w20;
	public static int w30;
	public static int w40;
	public static int w50;
	public static int w60;
	public static int w70;
	public static int w80;
	public static int w90;
	public static int w100;
	public static int w461;
	public static int w170;
	public static int w25;
	public static int w360;
	//
	public static int h5;
	public static int h10;
	public static int h20;
	public static int h30;
	public static int h40;
	public static int h50;
	public static int h70;
	public static int h60;
	public static int h90;
	public static int h100;
	public static int h150;
	public static int h95;
	public static int h278;
	public static int h120;
	public static int h45;
	public static int h80;

	public static int textSize21 = 21;
	public static int textSize22 = 22;
	public static int textSize23 = 23;
	public static int textSize25 = 25;
	public static int textSize30 = 30;
	public static int textSize20 = 20;
	public static int textSize15 = 15;
	public static int textSize13 = 13;
	public static int textSize10 = 10;
	public static int textSize17 = 17;
	public static int textSize12 = 12;
	public static int textSize18 = 18;
	public static int textSize40 = 40;
	public static int textSize35 = 35;
	public static int textSize36 = 36;

	public Cals(int width, int height, int mWidth, int mHeight) {
		if (width > height) {
			width = mHeight;
			height = mWidth;
		}
		textSize10 = (width * 10) / 720;
		textSize12 = (width * 12) / 720;
		textSize13 = (width * 13) / 720;
		textSize15 = (width * 15) / 720;
		textSize20 = (width * 20) / 720;
		textSize21 = (width * 21) / 720;
		textSize22 = (width * 22) / 720;
		textSize23 = (width * 23) / 720;
		textSize25 = (width * 25) / 720;
		textSize30 = (width * 30) / 720;
		textSize17 = (width * 17) / 720;
		textSize18 = (width * 18) / 720;
		textSize35 = (width * 35) / 720;
		textSize36 = (width * 36) / 720;
		textSize40 = (width * 40) / 720;
		w5 = (int) (0.6944 * width) / 100;
		w10 = (int) (1.3888 * width) / 100;
		w20 = (int) (2.7777 * width) / 100;
		w30 = (int) (4.1666 * width) / 100;
		w40 = (int) (5.5555 * width) / 100;
		w50 = (int) (6.9444 * width) / 100;
		w60 = (int) (8.3333 * width) / 100;
		w70 = (int) (9.7222 * width) / 100;
		w80 = (int) (11.1111 * width) / 100;
		w90 = (int) (12.5 * width) / 100;
		w100 = (int) (13.8888 * width) / 100;
		w461 = (int) (64.0278 * width) / 100;
		w170 = (int) (23.61 * width) / 100;
		w25 = (int) (3.47 * width) / 100;
		w360 = (int) (50 * width) / 100;

		//
		h5 = (int) (0.3906 * height) / 100;
		h10 = (int) (0.78125 * height) / 100;
		h20 = (int) (1.5625 * height) / 100;
		h30 = (int) (2.34375 * height) / 100;
		h50 = (int) (3.90625 * height) / 100;
		h40 = (int) (3.125 * width) / 100;
		h60 = (int) (4.6875 * width) / 100;
		h70 = (int) (5.46875 * height) / 100;
		h80 = (int) (6.25 * height) / 100;
		h90 = (int) (7.03125 * height) / 100;
		h100 = (int) (7.8125 * height) / 100;
		h150 = (int) (11.71875 * height) / 100;
		h120 = (int) (9.375 * height) / 100;
		h278 = (int) (21.71875 * height) / 100;
		h95 = (int) (7.421875 * height) / 100;
		h45 = (int) (3.515625 * height) / 100;

	}
}
