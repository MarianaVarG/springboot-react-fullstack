package com.solera.backendcursojava.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "posts")
@EntityListeners(AuditingEntityListener.class) // for Created At date
public class PostEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue // autoincrement
    private long id;

    @Column(nullable = false)
    private String postId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Date expiresAt;

    @CreatedDate //
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id" /* column name on DB */)
    private UserEntity user; // Mapped by

    @ManyToOne // Muchos post pertenecen a un exposure
    @JoinColumn(name = "exposure_id")
    private ExposureEntity exposure;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ExposureEntity getExposure() {
        return exposure;
    }

    public void setExposure(ExposureEntity exposure) {
        this.exposure = exposure;
    }
}
