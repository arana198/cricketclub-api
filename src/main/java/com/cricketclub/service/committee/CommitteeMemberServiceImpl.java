package com.cricketclub.service.committee;

import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.user.UserBO;
import com.cricketclub.repository.committee.CommitteeMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CommitteeMemberServiceImpl implements CommitteeMemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeMemberServiceImpl.class);

	@Autowired
	private CommitteeMemberRepository committeeMemberRepository;

    @Override
    public Optional<CommitteeMemberBO> findById(Long id) {
        LOGGER.info("Finding committee member by id {}", id);
        return Optional.of(committeeMemberRepository.findOne(id));
    }

    @Override
	public List<CommitteeMemberBO> findByYear(final Integer year) {
        LOGGER.info("Finding committee member by year {}", year);
		return committeeMemberRepository.findByYear(year);
	}

    @Override
    public List<CommitteeMemberBO> findByUser(final UserBO userBO) {
        LOGGER.info("Finding committee member by user {}", userBO.getId());
        return committeeMemberRepository.findByUser(userBO);
    }

	@Override
	public Optional<CommitteeMemberBO> findByCommitteeRoleAndYear(final Integer committeeRoleId, final Integer year) {
        LOGGER.info("Finding committee member by committee role id {} and year {}", committeeRoleId, year);
		return committeeMemberRepository.findByCommitteeRoleAndYear(committeeRoleId, year);
	}

	@Override
	public void save(CommitteeMemberBO committeeMemberBO) {
        LOGGER.info("Saving committee member role for user: {}, role: {} and year: {}", committeeMemberBO.getUser().getId(), committeeMemberBO.getCommitteeRole().getId(), committeeMemberBO.getYear());
		committeeMemberRepository.save(committeeMemberBO);
	}

    @Override
    public void remove(CommitteeMemberBO committeeMemberBO) {
        LOGGER.info("Removing committee member with id: {}", committeeMemberBO.getId());
        committeeMemberRepository.delete(committeeMemberBO);
    }

}
