package cz.bicenc.asset.rest.controller;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetBadRequestException;
import cz.bicenc.asset.persistence.entity.Contact;
import cz.bicenc.asset.persistence.repository.ContactRepository;
import cz.bicenc.asset.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Tag(name = "Contact API")
@RestController
@RequestMapping("${spring.data.rest.base-path}/context/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @GetMapping()
    @ResponseBody
    @Operation(summary = "Find contacs")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
    public Page<Contact> find(
            @Parameter(description = "Query predicate") @QuerydslPredicate(root = Contact.class, bindings = ContactRepository.class) Predicate predicate,
            @Parameter(description = "Pageable parameters") @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return service.findAll(predicate,pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get an contact by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Contact.class)))
    public Contact getOne(@PathVariable("id") UUID assetId) {
        return service.get(assetId);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create new asset")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Contact.class)))
    public Contact create(@RequestBody Contact contact) {
        contact.setId(null);
        contact.setCreated(null);
        contact.setCreatedBy(null);
        return service.save(contact);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update an existing contact")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Contact.class)))
    public Contact update(@PathVariable("id") UUID contactId, @RequestBody Contact contact) {
        if (Objects.isNull(contact.getId())) {
            throw new AssetBadRequestException("Empty ID");
        } else if (!Objects.equals(contact.getId(), contactId)) {
            throw new AssetBadRequestException("ID doesnt match");
        }
        return service.update(contactId,contact);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete an asset by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Contact.class)))
    public void delete(@PathVariable("id") UUID assetId) {
        service.deleteWithHistory(assetId);
    }


}
