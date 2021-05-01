package pl.mat.model.db;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Builder
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="stock")
public class Bitcoin {

    private Long timeUpdated;

    private String stockName;

    private Double price;

}
