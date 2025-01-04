package com.example.focus.Repository;

import com.example.focus.Model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {
    Space findSpaceById(Integer id);
    Space findSpaceByName(String name);
}
