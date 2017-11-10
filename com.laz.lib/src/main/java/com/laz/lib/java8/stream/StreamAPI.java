package com.laz.lib.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StreamAPI {
	private static List<Article> articles = new ArrayList<Article>();

	public static void main(String[] args) {

		for (int i = 0; i < 5; i++) {
			ArrayList a = new ArrayList();
			if (i < 2) {
				a.add("Java");
			}
			articles.add(new Article(i + "", i + "a", a));
		}
		long start = System.currentTimeMillis();
		Optional<Article> opt = getFirstJavaArticle();
		System.out.println(System.currentTimeMillis()-start);
		
		
		Article a = getFirstJavaArticle11();
		System.out.println(System.currentTimeMillis()-start);
		System.out.println(a);
	}

	public static Optional<Article> getFirstJavaArticle() {
		return articles.stream()
				.filter(article -> article.getTags().contains("Java"))
				.findFirst();
	}

	public static Article getFirstJavaArticle11() {

		for (Article article : articles) {
			if (article.getTags().contains("Java")) {
				return article;
			}
		}

		return null;
	}

}
