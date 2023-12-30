package com.project.NewsFeed.repository;

import com.project.NewsFeed.entity.FutureProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FutureProgramRepository extends JpaRepository<FutureProgram , Long> {
}
