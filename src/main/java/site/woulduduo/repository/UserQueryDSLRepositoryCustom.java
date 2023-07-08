package site.woulduduo.repository;

import site.woulduduo.dto.request.page.UserSearchType;
import site.woulduduo.dto.request.user.UserCommentRequestDTO;
import site.woulduduo.dto.response.user.UserProfileResponseDTO;
import site.woulduduo.entity.Follow;
import site.woulduduo.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserQueryDSLRepositoryCustom {

    List<UserProfileResponseDTO> getUserProfileList(UserSearchType userSearchType, HttpSession session);

    List<User> followed(HttpSession session);

    Long modifyProfileCard(UserCommentRequestDTO dto, HttpSession session);

    Long deleteProfileCard(UserCommentRequestDTO dto, HttpSession session);

}