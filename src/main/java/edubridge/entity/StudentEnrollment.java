package edubridge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment_applications")
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 1000)
    private String remarks;

    @Column(name = "reference_no", nullable = false, unique = true, length = 20)
    private String referenceNo;

    @Column(name = "student_account_id", nullable = false)
    private Long studentAccountId;

    private String studentType;
    private String academicYear;
    private String semester;

    private String fullName;
    private LocalDate dateOfBirth;
    private String sex;
    private String email;
    private String contactNumber;

    @Column(columnDefinition = "TEXT")
    private String completeAddress;

    private String courseId;
    private Integer yearLevel;

    private String previousSchoolName;
    private String previousCourse;
    private Integer lastYearLevel;

    private String guardianFullName;
    private String guardianRelationship;
    private String guardianContactNumber;

    private String status;

    private LocalDateTime createdAt;

    public StudentEnrollment() {
    }

    @PrePersist
public void onCreate() {
    createdAt = LocalDateTime.now();

    if (status == null) {
        status = "PENDING";
    }

    if (referenceNo == null) {
        referenceNo = "EDU-" +
                UUID.randomUUID().toString()
                .substring(0, 8).toUpperCase();
    }
}

    // Getters and Setters

    public Long getId() { return id; }
    public String getReferenceNo() {
    return referenceNo;
}

    public Long getStudentAccountId() { return studentAccountId; }
    public void setStudentAccountId(Long studentAccountId) {
        this.studentAccountId = studentAccountId;
    }

    public String getStudentType() { return studentType; }
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() { return semester; }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCompleteAddress() { return completeAddress; }
    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getYearLevel() { return yearLevel; }
    public void setYearLevel(Integer yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getPreviousSchoolName() { return previousSchoolName; }
    public void setPreviousSchoolName(String previousSchoolName) {
        this.previousSchoolName = previousSchoolName;
    }

    public String getPreviousCourse() { return previousCourse; }
    public void setPreviousCourse(String previousCourse) {
        this.previousCourse = previousCourse;
    }

    public Integer getLastYearLevel() { return lastYearLevel; }
    public void setLastYearLevel(Integer lastYearLevel) {
        this.lastYearLevel = lastYearLevel;
    }

    public String getGuardianFullName() { return guardianFullName; }
    public void setGuardianFullName(String guardianFullName) {
        this.guardianFullName = guardianFullName;
    }

    public String getGuardianRelationship() {
        return guardianRelationship;
    }
    public void setGuardianRelationship(String guardianRelationship) {
        this.guardianRelationship = guardianRelationship;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }
    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }

    public String getStatus() { return status; }

        public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

   public void setStatus(String status) {
    this.status = status;
}
}