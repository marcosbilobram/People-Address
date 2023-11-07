package br.com.fiap.epictask.address;

import br.com.fiap.epictask.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(length = 9, nullable = false)
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode;

    @Column(length = 35, nullable = false)
    private String city;

    private Boolean isMain = false;

    @ManyToOne
    @JoinColumn(
            name = "us_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private User user;

    public Address(String street, Integer number, String zipCode, String city, Boolean isMain) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.isMain = isMain;
    }
}