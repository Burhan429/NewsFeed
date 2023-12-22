package com.project.NewsFeed.repository;

import com.project.NewsFeed.entity.FutureProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FutureProgramRepository extends JpaRepository<FutureProgram , UUID> {

}
