package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;
    //300개의 더미데이터 생성
    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,10000).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    //수정시간 테스트
    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L); //존재하는 번호로 테스트

        if(result.isPresent()) {

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    //단일 항목 검색 테스트
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook; //1
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); //2
        BooleanExpression expression = qGuestbook.title.contains(keyword); //3
        builder.and(expression);
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //5

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    //다중 항목 검색 테스트
    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        String keyword = "1";
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); // 1-----------------------
        builder.and(exAll); //2---------------
        builder.and(qGuestbook.gno.gt(0L)); // 3--------------
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
