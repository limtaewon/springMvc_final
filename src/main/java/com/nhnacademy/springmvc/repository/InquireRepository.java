package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.NotExistCategoryNameException;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;

public class InquireRepository implements PostRepository {
    private static long id = 0;
    private final Map<Long, Post> inquires = new HashMap<>();
    private final HttpServletRequest httpServletRequest;

    public InquireRepository(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean exists(long id) {
        return inquires.containsKey(id);
    }

    @Override
    public List<Post> getUserInquires() {
        String loginID = getSessionLogin(httpServletRequest);

        return (inquires.values()).stream().filter(post -> post.getUserID().equals(loginID))
                .sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Post> getAdminInquires() {
        return (inquires.values()).stream().filter(post -> !post.isAnswer())
                .sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toList());
    }

    public List<Post> categorySearchInquires(String category) {
        if (category.equals("") || category.equals("전체")) {
            return getAdminInquires();
        }
        notExistCategoryCheck(category);

        return (inquires.values()).stream().filter(post -> !post.isAnswer() && post.getCategory()
                        .equals(Category.valueOf(category)))
                .sorted(Comparator.comparing(Post::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Inquire writeInquires(String title, String content, Category category, List<String> imageFiles) {
        String loginID = getSessionLogin(httpServletRequest);
        Inquire inquire = Inquire.create(title, content, category, imageFiles);

        inquire.setId(++id);
        inquire.setUserID(loginID);

        inquires.put(id, inquire);

        return inquire;
    }

    @Override
    public Inquire getInquire(long id) {
        return exists(id) ? (Inquire) inquires.get(id) : null;
    }

    private void notExistCategoryCheck(String category) {
        try {
            Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new NotExistCategoryNameException(e.getMessage());
        }
    }
}
