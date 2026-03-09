package com.gisdev.library.apiAuth;


import com.gisdev.library.dto.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class BookControllerDocs {
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(value = RetentionPolicy.RUNTIME)
    @Operation(summary = "Create client. Authorized roles are: ADMIN.", responses = {
            @ApiResponse(responseCode = "200", description = "Client is successfully created!"),
            @ApiResponse(responseCode = "400", description = "Error in setting client's data!",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseError.class))})
    }, description = """
            Create a new client record with the provided data. The client's name and VAT number must be unique.
            """)
    public @interface CreateClientDoc {
    }

    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(value = RetentionPolicy.RUNTIME)
    @Operation(summary = "Update client. Authorized roles are: ADMIN.", responses = {
            @ApiResponse(responseCode = "200", description = "Client is successfully updated!"),
            @ApiResponse(responseCode = "400", description = "Error in setting client's data!",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseError.class))})
    }, description = """
            Update an existing client record with the provided data. The client's name and VAT number must be unique.
            """)
    public @interface UpdateClientDoc {
    }
}
