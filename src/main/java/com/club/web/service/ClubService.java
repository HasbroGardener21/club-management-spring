package com.club.web.service;

import com.club.web.dto.ClubDto;
import com.club.web.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    Club updateClub(ClubDto clubDto);

    void delete(Long clubId);
}
