package uz.ulugbek.blog.controller;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uz.ulugbek.blog.dto.BlogDTO;
import uz.ulugbek.blog.errors.BadRequestAlertException;
import uz.ulugbek.blog.service.BlogService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Author: Ulug'bek Ro'zimboyev  <ulugbekrozimboyev@gmail.com>
 * Date: 14.08.2020 0:31
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class BlogController {

    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    private static final String ENTITY_NAME = "blog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * {@code POST  /blogs/create} : Create a new blog.
     *
     * @param blogDTO the blogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogDTO, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws java.net.URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogs/create")
    public ResponseEntity<BlogDTO> createBlog(@ModelAttribute BlogDTO blogDTO) throws Exception {
        log.debug("REST request to save Blog : {}", blogDTO);
        if (blogDTO.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogDTO result = blogService.save(blogDTO);
        return ResponseEntity.created(new URI("/api/blogs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code POST  /blogs/update} : Updates an existing blog.
     *
     * @param blogDTO the blogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogDTO,
     * or with status {@code 400 (Bad Request)} if the blogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogDTO couldn't be updated.
     * @throws java.net.URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogs/update")
    public ResponseEntity<BlogDTO> updateBlog(@ModelAttribute BlogDTO blogDTO) throws Exception {
        log.debug("REST request to update Blog : {}", blogDTO);
        if (blogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlogDTO result = blogService.update(blogDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDTO>> getAllBlogs(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Blogs");
        Page<BlogDTO> page = blogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blogs/:id} : get the "id" blog.
     *
     * @param id the id of the blogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blogs/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<BlogDTO> blogDTO = blogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogDTO);
    }

    /**
     * {@code DELETE  /blogs/:id} : delete the "id" blog.
     *
     * @param id the id of the blogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);
        blogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
}
