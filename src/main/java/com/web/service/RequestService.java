package com.web.service;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.dto.*;
import com.web.entity.QRequest;
import com.web.entity.Request;
import com.web.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    @PersistenceContext
    EntityManager em;
    private final RequestRepository requestRepository;

    public Long save(RequestFormDto requestFormDto) throws Exception {
        Request request = requestFormDto.create();
        requestRepository.save(request);

        return request.getId();
    }

    @Transactional(readOnly = true)
    public void delete(Long requestId) throws Exception {
        requestRepository.deleteById(requestId);
    }

    public Page<Request> getMainPage(Pageable pageable) {
        QRequest request = QRequest.request;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Request> content = queryFactory
                .selectFrom(QRequest.request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QRequest.request)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public RequestDto getRequestInfo(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(EntityNotFoundException::new);
        RequestDto requestDto = RequestDto.of(request);
        return requestDto;
    }
}
