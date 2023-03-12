package local.intranet.quarkus.api.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import local.intranet.quarkus.api.model.entity.User;
import local.intranet.quarkus.api.model.repository.UserRepository;

/**
 * 
 * IMHO plonk class, I can do with a repository {@link local.intranet.quarkus.api.model.repository.UserRepository}
 * as you can see the use in the methods. At the moment, I have no idea if other services do not count on Dao.
 * So I won't even implement another Dao yet.
 * 
 * {@link UserDao} for {@link local.intranet.quarkus.api.model.entity.User}
 * 
 * @author radek.kadner
 *
 */
@ApplicationScoped
public class UserDao {

	// @Inject private EntityManager entityManager;

	@Inject
	private UserRepository userRepository;

	/**
	 * 
	 * findAll
	 * 
	 * @return {@link List}&lt;{@link User}&gt;
	 */
	public List<User> findAll() {
		final List<User> ret = new ArrayList<>();
		for (User u : userRepository.findAll()) {
			ret.add(u);
		}
		return ret;
	}

	/**
	 * 
	 * findByName
	 * 
	 * @param userName {@link String}
	 * @return {@link User}
	 */
	public User findByName(String userName) {
		return userRepository.findByName(userName);
	}

	/**
	 * 
	 * findById
	 * 
	 * @param id {@link Long}
	 * @return {@link User}
	 */
	public User findById(Long id) {
		// userRepository.findById(id) returns Optional<User>
		return userRepository.findById(id).get();
	}

	/**
	 * 
	 * save
	 * 
	 * @param user {@link User}
	 * @return {@link User}
	 */
	@Transactional
	public User save(User user) {
		/*
		if (user.getId() == null) {
			entityManager.persist(user);
		} else {
			entityManager.merge(user);
		}
		return user;
		*/
		return userRepository.save(user);
	}

	/**
	 * 
	 * remove
	 * 
	 * @param user {@link User}
	 */
	@Transactional
	public void remove(User user) {
		// entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
		userRepository.delete(user);
	}

}
