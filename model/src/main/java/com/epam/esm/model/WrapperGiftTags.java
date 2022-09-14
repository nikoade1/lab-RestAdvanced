package com.epam.esm.model;

import java.util.List;

public class WrapperGiftTags {
    private GiftCertificate giftCertificate;
    private List<Tag> tags;

    public WrapperGiftTags() {
    }

    public WrapperGiftTags(GiftCertificate giftCertificate, List<Tag> tags) {
        this.giftCertificate = giftCertificate;
        this.tags = tags;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
