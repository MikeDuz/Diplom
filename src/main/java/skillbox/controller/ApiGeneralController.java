package skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skillbox.dto.LikeAndModeration;
import skillbox.dto.WrapperResponse;
import skillbox.dto.calendar.CalendarDto;
import skillbox.dto.comment.CommentDto;
import skillbox.dto.globalSettig.GlobalSettingDto;
import skillbox.dto.init.InitDto;
import skillbox.dto.tag.TagDto;
import skillbox.service.*;
import skillbox.service.impl.CalendarService;
import skillbox.service.impl.InitService;


import java.security.Principal;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ApiGeneralController {

    private final InitService initResponse;
    private final GlobalSettingService settingsService;
    private final TagService tagService;
    private final CalendarService calendarService;
    private final PostService postService;
    private final CommentService commService;
    private final ImageService imageService;


    @GetMapping("init")
    public ResponseEntity<InitDto> init() {
        return new ResponseEntity<>(initResponse.getInit(), HttpStatus.OK);
    }

    @GetMapping("settings")
    public ResponseEntity<GlobalSettingDto> settings() {
        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping("tag")
    public ResponseEntity<TagDto> tag(@RequestParam(defaultValue = "all") String query) {
        return new ResponseEntity<>(tagService.getTag(query), HttpStatus.OK);
    }

    @GetMapping("calendar")
    public ResponseEntity<CalendarDto> calendar(@RequestParam(defaultValue = "0") int year) {
        return new ResponseEntity<>(calendarService.getCalendar(year), HttpStatus.OK);
    }

    @PutMapping("settings")
    @PreAuthorize("hasAuthority('user:moderate')")
    public void changeSettings(@RequestBody GlobalSettingDto globalSettings,
                               Principal principal) {
        settingsService.setGlobalSetting(globalSettings, principal);
    }

    @PostMapping("moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<WrapperResponse> moderatePost(@RequestBody LikeAndModeration likeAndMod,
                                                        Principal principal) {
        return new ResponseEntity<>(postService.moderatePost(likeAndMod, principal), HttpStatus.OK);
    }

    @PostMapping("comment")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<WrapperResponse> insertComment(@RequestBody CommentDto comment,
                                          Principal principal) {
        return new ResponseEntity<>(commService.insertComment(comment, principal), HttpStatus.OK);
    }

    @PostMapping("image")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<String> getImage(@RequestParam("image") MultipartFile image) throws Exception {
        return new ResponseEntity<>(imageService.imageTreatment(image), HttpStatus.OK);
    }

    @PostMapping("profile/my")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<String> editProfile() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("statistics/my")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<String> myStatistic() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("statistics/all")
    @PreAuthorize("hasAnyAuthority('user:write', 'user:moderate')")
    public ResponseEntity<String> generalStatistic() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }





}
