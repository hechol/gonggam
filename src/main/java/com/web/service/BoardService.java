package com.web.service;

import com.web.dto.BoardDto;
import com.web.entity.BoardEntity;
import com.web.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    public Long register(BoardDto boardDTO) {

        BoardEntity board = modelMapper.map(boardDTO, BoardEntity.class);

        Long bno = boardRepository.save(board).getId();

        return bno;
    }

    public List<BoardEntity> list() {

        List<BoardEntity> all = boardRepository.findAll();

        return all;
    }

    public BoardDto readOne(Long id) {

        Optional<BoardEntity> result = boardRepository.findById(id);

        BoardEntity board = result.orElseThrow();

        BoardDto boardDto = modelMapper.map(board, BoardDto.class);

        return boardDto;
    }

    public void remove(Long id) {

        boardRepository.deleteById(id);

    }
}
