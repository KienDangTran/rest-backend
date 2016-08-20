package com.giong.api.service.master;

import com.giong.api.persistence.domain.IdScheme;
import com.giong.api.repository.idschema.IdSchemeRepository;
import com.giong.api.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service("schemeService")
public class SchemeService extends BaseService<IdScheme, Integer, IdSchemeRepository> {

	public IdScheme getSchemeByName(String schemeName) {
		final List<IdScheme> schemes = this.repository.getSchemeByName(schemeName);
		if (!schemes.isEmpty())
			return schemes.get(0);
		else
			return null;
	}

	public String generateNextId(String schemeName) {
		final IdScheme scheme = this.getSchemeByName(schemeName);
		if (scheme != null) {
			final String prefix = StringUtils.isEmpty(scheme.getPrefix()) ? "" : scheme.getPrefix();
			final String suffix = StringUtils.isEmpty(scheme.getSuffix()) ? "" : scheme.getSuffix();
			final String lastGenNo = String.valueOf(scheme.getLastGenNo() + 1);
			final StringBuilder codeBuilder = new StringBuilder(prefix);

			for (int i = 1; i <= scheme.getLength() - lastGenNo.length() - prefix.length() - suffix.length(); i++) {
				codeBuilder.append(scheme.getFilledChar());
			}
			codeBuilder.append(lastGenNo).append(suffix);
			this.increaseLastGenNo(scheme);
			return codeBuilder.toString();
		} else
			throw new IllegalArgumentException("Cannot find Scheme with name: " + schemeName);
	}

	public String getLastGeneratedId(String schemeName) {
		final IdScheme scheme = this.getSchemeByName(schemeName);
		if (scheme != null) {
			final String prefix = StringUtils.isEmpty(scheme.getPrefix()) ? "" : scheme.getPrefix();
			final String suffix = StringUtils.isEmpty(scheme.getSuffix()) ? "" : scheme.getSuffix();
			final String lastGenNo = String.valueOf(scheme.getLastGenNo());
			final StringBuilder codeBuilder = new StringBuilder(prefix);

			for (int i = 1; i <= scheme.getLength() - lastGenNo.length() - prefix.length() - suffix.length(); i++) {
				codeBuilder.append(scheme.getFilledChar());
			}
			codeBuilder.append(lastGenNo).append(suffix);
			this.increaseLastGenNo(scheme);
			return codeBuilder.toString();
		} else
			throw new IllegalArgumentException("Cannot find Scheme with name: " + schemeName);
	}

	public void increaseLastGenNo(IdScheme scheme) {
		if (scheme != null) {
			scheme.setLastGenNo(scheme.getLastGenNo() + 1);
			this.repository.save(scheme);
		}
	}
}
