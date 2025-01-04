package com.example.focus.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Photographer {
    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "City cannot be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String city;
    @Pattern(regexp = "^05[0-9]{8}$", message = "Phone number must start with 05 and be followed by 8 digits")
    @Column(columnDefinition = "varchar(20) not null")
    private String phone;



    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "photographer_id", referencedColumnName = "id")
    private MyUser myUser;

//    @OneToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    @JsonIgnore
//    private ProfilePhotographer profilePhotographer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "photographer")
    private Set<Tool> tools;

    @OneToMany(mappedBy = "renter")
    private Set<RentTools> myOrders;  // Orders for tools this photographer rents from others

    @OneToMany(mappedBy = "owner")
    private Set<RentTools> rentalTools; // Rental transactions for tools this photographer owns




//    @OneToMany(mappedBy = "renter")
//    private Set<RentTools> myOrders;  // Orders for tools this photographer rents from others
//
//    @OneToMany(mappedBy = "owner")
//    private Set<RentTools> rentalTools; // Rental transactions for tools this photographer owns


}