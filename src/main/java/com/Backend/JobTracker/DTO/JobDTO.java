package com.Backend.JobTracker.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobDTO {
    private Long id;
    private String CompanyName;
    private String Role;
    private LocalDate ApplyDate;
    private String status;
    private String Notes;
    private String resumeUrl;

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", CompanyName='" + CompanyName + '\'' +
                ", Role='" + Role + '\'' +
                ", ApplyDate=" + ApplyDate +
                ", status='" + status + '\'' +
                ", Notes='" + Notes + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                '}';
    }

}
