# XenClient secure backend-domain image

include xenclient-image-common.inc
IMAGE_FEATURES += "package-management"

COMPATIBLE_MACHINE = "(xenclient-ndvm)"

IMAGE_FSTYPES = "xc.ext3.vhd.gz"

BAD_RECOMMENDATIONS += "avahi-daemon avahi-autoipd"
# The above seems to be broken and we *really* don't want avahi!
PACKAGE_REMOVE = "avahi-daemon avahi-autoipd hicolor-icon-theme"

export IMAGE_BASENAME = "xenclient-ndvm-image"

ANGSTROM_EXTRA_INSTALL += ""

DEPENDS = "task-base"
IMAGE_INSTALL = "\
    ${ROOTFS_PKGMANAGE} \
    modules \
    task-core-boot \
    task-base \
    task-xenclient-common \
    util-linux-mount \
    util-linux-umount \
    busybox \
    openssh \
    kernel-modules \
    libv4v \
    libv4v-bin \
    dbus \
    xenclient-dbusbouncer \
    networkmanager \
    xenclient-toolstack \
    intel-e1000e \
    intel-e1000e-conf \
    linux-firmware \
    rt2870-firmware \
    rt3572 \
    bridge-utils \
    iptables \
    xenclient-ndvm-tweaks \
    ipsec-tools \
    rsyslog \
    ${ANGSTROM_EXTRA_INSTALL} \
    xenclient-udev-force-discreet-net-to-eth0 \
    v4v-module \
    xen-tools-libxenstore \
    xen-tools-xenstore-utils \
    wget \
    ethtool \
    carrier-detect \
    xenclient-nws \
    modemmanager \
    ppp \
    iputils-ping \
"

# Packages disabled for Linux3 to be fixed
# rt5370

#IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

#zap root password for release images
ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

ROOTFS_POSTPROCESS_COMMAND += "sed -i 's|root:x:0:0:root:/home/root:/bin/sh|root:x:0:0:root:/root:/bin/bash|' ${IMAGE_ROOTFS}/etc/passwd;"

ROOTFS_POSTPROCESS_COMMAND += "echo '1.0.0.0 dom0' >> ${IMAGE_ROOTFS}/etc/hosts;"

# enable ctrlaltdel reboot because PV driver uses ctrl+alt+del to interpret reboot issued via xenstore
ROOTFS_POSTPROCESS_COMMAND += "echo 'ca:12345:ctrlaltdel:/sbin/shutdown -t1 -a -r now' >> ${IMAGE_ROOTFS}/etc/inittab;"

# Move resolv.conf to /var/volatile/etc, as rootfs is readonly
ROOTFS_POSTPROCESS_COMMAND += "rm -f ${IMAGE_ROOTFS}/etc/resolv.conf; ln -s /var/volatile/etc/resolv.conf ${IMAGE_ROOTFS}/etc/resolv.conf;"

ROOTFS_POSTPROCESS_COMMAND += "opkg-cl ${IPKG_ARGS} -force-depends \
                                remove ${PACKAGE_REMOVE};"

inherit selinux-image
#inherit validate-package-versions
inherit xenclient-image-src-info
inherit xenclient-image-src-package
inherit xenclient-licences
require xenclient-version.inc

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=751419260aa954499f7abaabaa882bbe      \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=3da9cfbcb788c80a0384361b4de20420"
