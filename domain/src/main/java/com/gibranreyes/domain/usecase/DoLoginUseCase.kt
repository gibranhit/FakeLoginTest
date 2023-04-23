package com.gibranreyes.domain.usecase

import com.gibranreyes.core.ext.isEmail
import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.repository.AuthRepository
import com.gibranreyes.domain.R
import com.gibranreyes.domain.converter.ResourceConverter
import com.gibranreyes.domain.mappers.toDomainModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceConverter: ResourceConverter,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) = flow {
        if (email.isEmail().not()) {
            emit(
                Outcome.Error(
                    resourceConverter.convertString(R.string.invalid_username),
                ),
            )
            return@flow
        }

        if (password.isBlank()) {
            emit(
                Outcome.Error(
                    resourceConverter.convertString(R.string.invalid_password),
                ),
            )
            return@flow
        }
        authRepository.doLogin(
            email,
            password,
        ).collect {
            when (it) {
                is Outcome.Success -> {
                    emit(
                        Outcome.Success(
                            it.data.toDomainModel(),
                        ),
                    )
                }

                is Outcome.Error -> {
                    emit(
                        Outcome.Error(
                            it.message,
                        ),
                    )
                }
            }
        }
    }
}
