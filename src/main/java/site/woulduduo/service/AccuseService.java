package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.request.accuse.UserAccuseRequestDTO;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.accuse.AccuseListResponseDTO;
import site.woulduduo.dto.response.page.PageResponseDTO;
import site.woulduduo.entity.Accuse;
import site.woulduduo.entity.User;
import site.woulduduo.repository.AccuseRepository;
import site.woulduduo.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccuseService {

    private final AccuseRepository accuseRepository;
    private final UserRepository userRepository;


    //신고내역(ADMIN)

    public ListResponseDTO<AccuseListResponseDTO,Accuse> getAccuseListByAdmin(PageDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPage()-1,
                dto.getSize(),
                Sort.by("accuseNo").descending()
        );

        System.out.println("pageable = " + pageable);

        Page<Accuse> all = accuseRepository.findAll(pageable);

        System.out.println("all = " + all);
        List<AccuseListResponseDTO> collect = all.stream()
                .map(AccuseListResponseDTO::new)
                .collect(toList());

        System.out.println("collect = " + collect);
        return ListResponseDTO.<AccuseListResponseDTO,Accuse>builder()
                .count(collect.size())
                .pageInfo(new PageResponseDTO<>(all))
                .list(collect)
                .build();

    }

    public ListResponseDTO<AccuseListResponseDTO,Accuse> getAccuseTodayListByAdmin(PageDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("accuseNo").descending()
        );

        System.out.println("pageable = " + pageable);

        String userAccount = dto.getKeyword();

        Page<Accuse> all = accuseRepository.findAll(pageable);
        System.out.println("all-------- = " + all);
        List<Accuse> todayAccuseList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Accuse accuse : all) {
            System.out.println("accuseByAdminResponseDTO = " + accuse);
            LocalDateTime localDateTime = accuse.getAccuseWrittenDate();
            LocalDate localDate = localDateTime.toLocalDate();
            if (localDate != null && localDate.equals(currentDate)) {
                todayAccuseList.add(accuse);
                System.out.println("accuse----- = " + accuse);
            }

        }

        List<AccuseListResponseDTO> collect = todayAccuseList.stream()
                .map(AccuseListResponseDTO::new)
                .collect(toList());

            System.out.println("collect----- = " + collect);


            System.out.println("collect = " + collect);
            return ListResponseDTO.<AccuseListResponseDTO, Accuse>builder()
                    .count(collect.size())
                    .pageInfo(new PageResponseDTO<>(all))
                    .list(collect)
                    .build();


    }






        //금일 작성 게시물 (ADMIN)
    public ListResponseDTO<AccuseListResponseDTO,Accuse> todayAccuseByAdmin(PageDTO dto){
        ListResponseDTO<AccuseListResponseDTO,Accuse> accuseListByAdmin = getAccuseListByAdmin(dto);
        List<AccuseListResponseDTO> todayAccuseList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (AccuseListResponseDTO accuseByAdminResponseDTO : todayAccuseList) {
            System.out.println("accuseByAdminResponseDTO = " + accuseByAdminResponseDTO);
            LocalDate writtenDate = accuseByAdminResponseDTO.getAccuseWrittenDate();
            if(writtenDate!=null&&writtenDate.equals(currentDate)){
                todayAccuseList.add(accuseByAdminResponseDTO);
            }
        }
        System.out.println("todayAccuseList = " + todayAccuseList);
//        return todayAccuseList;
        return null;

    }

    //경고 데이터 전달
    public boolean accuseUser(UserAccuseRequestDTO dto){

        String userNickname = dto.getUserNickname();
        User byUserAccount = userRepository.findByUserNickname(userNickname);
        System.out.println("byUserAccount = " + byUserAccount);
        //정보 없으면 false;
        if(byUserAccount==null){
            return false;
        }

        Accuse entity = dto.toEntity();
        entity.setUser(byUserAccount);

        String accuseType = entity.getAccuseType();
        System.out.println("accuseType = " + accuseType);

        String accuseEtc = entity.getAccuseEtc();
        System.out.println("accuseEtc123123  = " + accuseEtc);

        if(accuseEtc!=null||accuseType!=null) {
            Accuse save = accuseRepository.save(entity);
            System.out.println("save = " + save);
            return true;
        }
        return false;
    }

    public int accuseUserCount(UserAccuseRequestDTO dto) {
        System.out.println("dto = " + dto);
        String userNickname = dto.getUserNickname();
        User byUserNickName = userRepository.findByUserNickname(userNickname);
        System.out.println("byUserNickName = " + byUserNickName);
        List<Accuse> accuseList = byUserNickName.getAccuseList();
        int size = accuseList.size();
        System.out.println("size = " + size);
        return size;
    }
}
