package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.domain.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getUserInquires();

    List<Post> getAdminInquires();

    Inquire writeInquires(String title, String content, Category category, List<String> imageFiles);

    Inquire getInquire(long id);

    boolean exists(long id);

    List<Post> categorySearchInquires(String category);
}
