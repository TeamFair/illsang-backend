package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.user.dto.request.UserCouponUpdateRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_coupon")
class UserCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long = 0,

    @Column(name = "coupon_id", nullable = false)
    var couponId: Long = 0,

    @Column(name = "use_yn", nullable = false)
    var couponUseYn: Boolean = false,

    @Column(name = "expire_yn", nullable = false)
    var couponExpireYn: Boolean = false,

    @Column(name = "used_at")
    var usedAt: LocalDateTime? = null


) : BaseEntity() {

    companion object {
        // 발급 팩토리: 항상 미사용/미만료 상태로 시작하도록 강제
        fun issue(userId: Long, couponId: Long): UserCouponEntity {
            return UserCouponEntity(
                userId = userId,
                couponId = couponId,
                couponUseYn = false,
                couponExpireYn = false,
                usedAt = null
            )
        }
    }

    fun use() {
        ensureNotExpired()
        ensureNotUsed()
        this.couponUseYn = true
        this.usedAt = LocalDateTime.now()
        this.updatedAt = LocalDateTime.now()
    }


    // update(request)로 들어오는 상태 변경을 도메인 규칙으로 해석/제한
    fun update(request: UserCouponUpdateRequest) {
        // 사용 요청 처리
        request.couponUseYn?.let { wantUse ->
            if (wantUse) {
                this.use()
            } else {
                // 한 번 사용된 쿠폰은 미사용으로 되돌릴 수 없음
                if (this.couponUseYn) {
                    throw IllegalStateException("이미 사용된 쿠폰은 미사용으로 되돌릴 수 없습니다.")
                }
                // 이미 미사용이면 변화 없음
            }
        }

        // 만료 요청 처리
        request.couponExpireYn?.let { wantExpire ->
            if (wantExpire) {
                this.expire()
            } else {
                // 한 번 만료되면 만료 해제 불가
                if (this.couponExpireYn) {
                    throw IllegalStateException("이미 만료된 쿠폰은 만료 해제할 수 없습니다.")
                }
                // 이미 미만료이면 변화 없음
            }
        }

        // 도메인 명령에서 타임스탬프 처리하므로 여기서는 마지막 보정만
        this.updatedAt = LocalDateTime.now()
    }


    private fun ensureNotExpired() {
        if (this.couponExpireYn) {
            throw IllegalStateException("만료된 쿠폰은 사용할 수 없습니다.")
        }
    }

    private fun ensureNotUsed() {
        if (this.couponUseYn) {
            throw IllegalStateException("이미 사용된 쿠폰은 다시 사용할 수 없습니다.")
        }
    }

    // 만료 명령: 만료 해제 금지 정책 강제
    fun expire() {
        if (this.couponExpireYn) {
            throw IllegalStateException("이미 만료된 쿠폰은 만료 해제할 수 없습니다.")
        }
        this.couponExpireYn = true
        this.updatedAt = LocalDateTime.now()
    }

}