DESCRIPTION = "XenClient capabilities"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "file://caps.default"

inherit xenclient

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/caps.default ${D}${sysconfdir}/
}
