package com.solera.backendcursojava.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an exposure entity in the system.
 * An exposure is associated with multiple posts.
 */
@Entity(name = "exposures")
public class ExposureEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue // Auto-increment
    private long id;

    @Column(nullable = false, length = 250)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exposure")
    private List<PostEntity> posts = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }
}
