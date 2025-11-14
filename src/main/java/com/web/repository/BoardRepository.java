package com.web.repository;

import com.web.constant.BoardCategory;
import com.web.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByCategory(BoardCategory boardCategory);
}