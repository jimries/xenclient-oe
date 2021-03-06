DESCRIPTION = "PV linux audio alsa driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=751419260aa954499f7abaabaa882bbe"

PV = "0+git${SRCPV}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://${OPENXT_GIT_MIRROR}/pv-linux-drivers.git;protocol=${OPENXT_GIT_PROTOCOL};branch=${OPENXT_BRANCH}"
DEPENDS = "virtual/kernel v4v-module"

S = "${WORKDIR}/git/xc-audio"

inherit xenclient 
inherit module

do_compile() {
    make -C ${STAGING_KERNEL_DIR} M="${S}" KSRC="${STAGING_KERNEL_DIR}" EXTRA_CFLAGS="-DXC_HAS_STATIC_XEN"
}

do_install() {
    make -C ${STAGING_KERNEL_DIR} KSRC="${STAGING_KERNEL_DIR}" DEPMOD=echo INSTALL_MOD_PATH="${D}" M="${S}" modules_install
}

FILES_${PN} = "/lib/modules /etc/modprobe.d"
