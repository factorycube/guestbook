// 페이지 요청 처리 DTO(PageRequestDTO)
// 목록 페이지를 요청할 때 사용하는 데이터를 재사용
// 페이지 번호, 페이지 내 목록의 개수, 검색 조건 파라미터 나중에 자사용하는 용도
// 화면에서 전달되는 목록 관련된 데이터에 대한 DTO
// 화면에서 전달되는 page 라는 파라미터와 size 라는 파라미터를 수집하는 역할
// 페이지 번호 등은 기본값을 가지는 것이 좋기 때문에 1 과 10 이라는 값을 이용
// PageRequestDTO 의 진짜 목적은 JPA 쪽에서 사용하는 Pageable 타입의 객체를 생성하는 것
// 나중에,
// 수정의 여지(페이지 번호에 음수가 들어오는 등)가 있기는 하지만, JPA 를 이용하는 경우에는 페이지 번호가
// 0 부터 시작한다는 점을 감안해서 1 페이지의 경우 0 이 될 수 있도록 page-1 을 하는 형태로 작성
// 정렬은 나중에 다양한 상황에서 쓰기 위해서 별도의 파라미터로 받도록 설계
package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type; // 검색 조건
    private String keyword; // 검색 키워드

    public PageRequestDTO() {
        this.page = 1;  // 첫 페이지 번호
        this.size = 10; // 목록 페이지글 갯수
    }
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
