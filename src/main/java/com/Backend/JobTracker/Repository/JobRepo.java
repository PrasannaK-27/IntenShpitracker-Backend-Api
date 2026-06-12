package com.Backend.JobTracker.Repository;

import com.Backend.JobTracker.DTO.JobDTO;
import com.Backend.JobTracker.Entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Jobs , Long> {

    List<Jobs> findByStatus(String applied);

    Jobs findByResumeUrl(String fileName);

    @Query("""
            SELECT new com.Backend.JobTracker.DTO.JobDTO(
                   j.id,
                   j.CompanyName,
                   j.Role,
                   j.ApplyDate,
                   j.status,
                   j.Notes,
                   j.resumeUrl)
            FROM Jobs j
            """)
    List<JobDTO> getAllJobs();

    @Query("""
            SELECT new com.Backend.JobTracker.DTO.JobDTO(
                   j.id,
                   j.CompanyName,
                   j.Role,
                   j.ApplyDate,
                   j.status,
                   j.Notes,
                   j.resumeUrl)
            FROM Jobs j where j.id = id
            """)
    JobDTO getAllJobsById(Long id);
}
