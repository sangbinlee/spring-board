package com.smartscore.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartscore.board.repository.Dir;
import com.smartscore.board.repository.DirRepository;
import com.smartscore.board.repository.FilesRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "QR 컨트롤러", description = "QR코드 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dir")
public class DirRestController {

    private final DirRepository dirRepository;
    private final FilesRepository filesRepository;

//    public DirRestController(DirRepository dirRepository, FilesRepository filesRepository) {
//        this.dirRepository = dirRepository;
//        this.filesRepository = filesRepository;
//    }

//    @GetMapping
//    public List<MenuResult> getV2Menus() {
//    	final List<Dir> all = dirRepository.findAllByParentIsNull();
//    	return all.stream().map(MenuResult::new).collect(Collectors.toList());
//    }
    @Operation(summary = "QR 스캔", description = "사용자가 배송된 물품의 QR코드를 스캔합니다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "스캔 성공", content = @Content(schema = @Schema(implementation = ResponseData.class))),
//            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema(implementation = ResponseError.class))),
//            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ResponseError.class))),
//            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = ResponseError.class)))
//    })
    @GetMapping
    public ResponseData getUsers() {
//    	public Map<String, Object> getUsers() {
//    	Optional<Dir> dirList = dirRepository.findById((long) 1);
    	List<Dir> dirList = dirRepository.findAll();
//    	List<Dir> fileList2 =dirList.stream().collect(Collectors.toList());
//    	List<Files> fileList = filesRepository.findAll();
//		Set<Dir> result = list.stream().collect(Collectors.toSet());
//		dir.setChildren(result);
		Long menu1 = (long) 1;
		Long menu2 = (long) 2;
		Long menu3 = (long) 3;
		for (Dir dir : dirList) {

		}
		List<Dir> menu1Root = dirList.stream().filter(x-> x.getId() == menu1).collect(Collectors.toList());
		List<Dir> menu2listWithOutRoot = dirList.stream().filter(x-> x.getParent() != null).collect(Collectors.toList());
		List<Dir> menu1list = menu2listWithOutRoot.stream().filter(x-> x.getParent().getId() == menu1).collect(Collectors.toList());
		for (Dir dir : menu1list) {
			Long parentId = dir.getId();
			List<Dir> menu2list = menu2listWithOutRoot.stream().filter(x-> x.getParent().getId() == parentId).collect(Collectors.toList());
			Set<Dir> result = menu2list.stream().collect(Collectors.toSet());
			dir.setChildren(result);
		}
		List<Dir> menu3list = menu2listWithOutRoot.stream().filter(x-> x.getParent().getId() == menu3).collect(Collectors.toList());

//		{
//			  name: 'My Tree',
//			  children: []
//		}
		Map<String, Object> treeMap = new HashMap<String, Object>();
		treeMap.put("name", menu1Root.get(0).getName());
		treeMap.put("children", menu1list);
		ResponseData responseData = new ResponseData(null, null, treeMap, null);
//        return treeMap;
        return responseData;
    }

    @GetMapping("/{id}")
    public Dir getUsers(@PathVariable Long id) {
        return this.dirRepository.findById(id).orElse(null);
    }
}
