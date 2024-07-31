package com.backend.model;

import com.backend.util.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "url_slug", nullable = false, unique = true)
    private String urlSlug;

    @ManyToOne
    @JoinColumn(name = "parent_cat_id")
    private Categories parentCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CategoryStatus status;
}
