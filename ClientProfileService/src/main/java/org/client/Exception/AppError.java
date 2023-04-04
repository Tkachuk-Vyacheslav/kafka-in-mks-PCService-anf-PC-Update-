package org.client.Exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppError {

    private int statusCode;
    private String message;

}
