package com.h.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MoviesService {

    private static final List<Movies> movies = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    public  void MovieSystem() {
        while (true) {
            System.out.println("1. 添加电影");
            System.out.println("2. 删除电影");
            System.out.println("3. 修改电影");
            System.out.println("4. 查询电影");
            System.out.println("5. 显示所有电影");
            System.out.println("6. 退出系统");
            System.out.println("请输入您的选择：");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addMovie();
                    break;

                case 2:
                    deleteMovie();
                    break;

                case 3:
                    updateMovie();
                    break;

                case 4:
                    queryMovie();
                    break;

                case 5:
                    showMovies();
                    break;

                case 6:
                    System.out.println("退出系统");
                    System.exit(0);
                    break;

                default:
                    System.out.println("输入错误，请重新输入");
                    break;

            }
        }
    }

    private void showMovies() {
        for (Movies movie : movies) {
            System.out.println(movie);
        }
    }

    private void queryMovie() {
        System.out.println("请输入电影名称：");
        String name = sc.next();
        for (Movies movie : movies){
            if(movie.getName().contains(name)){
                System.out.println(movie);
                return;
            }
        }
        System.out.println("未找到该电影");
    }

    private void updateMovie() {
        System.out.println("请输入电影编号：");
        int id = sc.nextInt();
        for (Movies movie : movies) {
            if (movie.getId() == id) {
                System.out.println("请输入电影名称：");
                String name = sc.next();
                System.out.println("请输入主演：");
                String actor = sc.next();
                System.out.println("请输入评分：");
                double score = sc.nextDouble();
                movie.setName( name);
                movie.setActor(actor);
                movie.setScore(score);
                System.out.println("修改成功");
                return;
            }
        }
        System.out.println("未找到该电影");
    }

    private void deleteMovie() {
        System.out.println("请输入电影编号：");
        int id = sc.nextInt();
        for (Movies movie : movies) {
            if (movie.getId() == id) {
                movies.remove(movie);
                System.out.println("删除成功");
                return;
            }
        }
        System.out.println("未找到该电影");
    }

    private void addMovie() {
        System.out.println("请输入电影名称：");
        String name = sc.next();
        System.out.println("请输入主演：");
        String actor = sc.next();
        System.out.println("请输入评分：");
        double score = sc.nextDouble();
        movies.add(new Movies(movies.size() + 1, name, actor, score));
        System.out.println("添加成功");
        System.out.println(movies);
    }

}
