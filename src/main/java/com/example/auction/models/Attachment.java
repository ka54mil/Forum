package com.example.auction.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
@Table(name = "attachments")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 50)
    private String type;

    @Size(min = 1, max = 20)
    private String suffix;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name="content", nullable=false)
    private byte[] content;

    @Transient
    private MultipartFile file;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

}
