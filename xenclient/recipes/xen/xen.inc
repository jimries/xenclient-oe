inherit xenclient
inherit xenclient-pq

SRCREV_FORMAT = "source_patchqueue"

PV = "${XEN_VERSION}+git${SRCPV}"

SRCREV_patchqueue = "${AUTOREV}"

SRC_URI = "${XEN_SRC_URI};name=source \
	  git://github.com/jimries/xen-common-pq.git;protocol=git;branch=master;destsuffix=patchqueue;name=patchqueue \
"

SRC_URI[source.md5sum] := "${XEN_SRC_MD5SUM}"
SRC_URI[source.sha256sum] := "${XEN_SRC_SHA256SUM}"

S = "${WORKDIR}/xen-${XEN_VERSION}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e0f0f3ac55608719a82394cc353928df"

do_apply_patchqueue_prepend() {
	cat >${WORKDIR}/patchqueue/master/guards <<EOF
xt-only
EOF
}
