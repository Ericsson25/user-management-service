package manuscript.module.user.management.searchuser;

import java.util.List;

import org.springframework.stereotype.Service;

import manuscript.module.user.management.bean.SearchUser;
import manuscript.module.user.management.bean.User;

@Service
public class SearchUserServiceImpl implements SearchUserService {

	private SearchUserDao searchUserDao;

	public SearchUserServiceImpl(SearchUserDao searchUserDao) {
		this.searchUserDao = searchUserDao;
	}

	@Override
	public List<User> searchUsers(SearchUser searchUser) {
		return searchUserDao.searchUsers(searchUser);
	}

}
