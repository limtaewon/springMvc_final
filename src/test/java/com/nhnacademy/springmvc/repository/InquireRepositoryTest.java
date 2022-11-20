package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.NotExistCategoryNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InquireRepositoryTest {
    private MockHttpServletRequest request;
    private PostRepository repository;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        repository = new InquireRepository(request);
        session = new MockHttpSession();

        session.setAttribute("login","customer");
        request.setSession(session);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    @Order(1)
    void not_exists() {
        boolean exists = repository.exists(1);
        assertThat(exists).isFalse();
    }

    @Test
    @Order(2)
    void exists() {
        repository.writeInquires("test", "test", Category.불만, new ArrayList<>());
        boolean exists = repository.exists(1);

        assertThat(exists).isTrue();
    }

    @Test
    @Order(3)
    void get_inquire() {
        Inquire saveInquire = repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        Inquire inquire = repository.getInquire(2);

        assertThat(inquire).isEqualTo(saveInquire);
    }

    @Test
    void writeInquires() {
        Inquire inquire = repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());

        assertThat(inquire.getCategory()).isEqualTo(Category.불만);
        assertThat(inquire.getTitle()).isEqualTo("test1");
        assertThat(inquire.getContent()).isEqualTo("test");
        assertThat(inquire.getImageFiles()).isEmpty();
    }

    @Test
    void getUserInquires() {
        repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());

        List<Post> userInquires = repository.getUserInquires();

        assertThat(userInquires).hasSize(1);
    }

    @Test
    void getAdminInquires() {
        Inquire inquire = repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test2", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test3", "test", Category.불만, new ArrayList<>());

        inquire.setAnswer(true);

        List<Post> adminInquires = repository.getAdminInquires();

        assertThat(adminInquires).hasSize(2);
    }

    @Test
    void success_category_search() {
        Inquire inquire = repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test2", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test3", "test", Category.불만, new ArrayList<>());

        inquire.setAnswer(true);

        List<Post> inquires = repository.categorySearchInquires("불만");

        assertThat(inquires).hasSize(2);
        assertThat(inquires.get(0).getCategory()).isEqualTo(Category.불만);
        assertThat(inquires.get(1).getCategory()).isEqualTo(Category.불만);
    }

    @Test
    void success_category_search_blank_and_reverseOrder() {
        repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test2", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test3", "test", Category.접수, new ArrayList<>());
        repository.writeInquires("test4", "test", Category.기타_문의, new ArrayList<>());
        repository.writeInquires("test5", "test", Category.제안, new ArrayList<>());

        List<Post> inquires = repository.categorySearchInquires("");

        assertThat(inquires).hasSize(5);
        assertThat(inquires.get(0).getCategory()).isEqualTo(Category.제안);
        assertThat(inquires.get(1).getCategory()).isEqualTo(Category.기타_문의);
        assertThat(inquires.get(2).getCategory()).isEqualTo(Category.접수);
        assertThat(inquires.get(3).getCategory()).isEqualTo(Category.불만);
        assertThat(inquires.get(4).getCategory()).isEqualTo(Category.불만);
    }

    @Test
    void success_category_search_all_and_reverseOrder() {
        repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test2", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test3", "test", Category.접수, new ArrayList<>());
        repository.writeInquires("test4", "test", Category.기타_문의, new ArrayList<>());
        repository.writeInquires("test5", "test", Category.제안, new ArrayList<>());

        List<Post> inquires = repository.categorySearchInquires("전체");

        assertThat(inquires).hasSize(5);
        assertThat(inquires.get(0).getCategory()).isEqualTo(Category.제안);
        assertThat(inquires.get(1).getCategory()).isEqualTo(Category.기타_문의);
        assertThat(inquires.get(2).getCategory()).isEqualTo(Category.접수);
        assertThat(inquires.get(3).getCategory()).isEqualTo(Category.불만);
        assertThat(inquires.get(4).getCategory()).isEqualTo(Category.불만);
    }

    @Test
    void failed_category_search() {
        repository.writeInquires("test1", "test", Category.불만, new ArrayList<>());
        repository.writeInquires("test2", "test", Category.불만, new ArrayList<>());

        Assertions.assertThatThrownBy(() -> repository.categorySearchInquires("틀리게 검색"))
                .isInstanceOf(NotExistCategoryNameException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("틀리게 검색");
    }

    @Test
    void get_inquire_null() {
        Inquire inquire = repository.getInquire(1);

        assertThat(inquire).isNull();
    }
}