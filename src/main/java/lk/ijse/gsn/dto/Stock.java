package lk.ijse.gsn.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Stock {
    private String Product_Item_Stock;
    private String qty;
    private String Description;
    private String brand;
}
