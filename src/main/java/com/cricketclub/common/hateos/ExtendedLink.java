package com.cricketclub.common.hateos;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(name = "link", namespace = Link.ATOM_NAMESPACE)
public class ExtendedLink extends Link implements Serializable {

    private static final long serialVersionUID = 4580040790923646222L;

    private String name;
    private String description;
    private String[] methods;

    public ExtendedLink() {}
    public static ExtendedLink extend(final Link link) {
        return new ExtendedLink(link.getHref(), link.getRel());
    }

    /**
     * Creates a new link to the given URI with the self rel.
     *
     * @see #REL_SELF
     * @param href must not be {@literal null} or empty.
     */
    public ExtendedLink(final String href) {
        super(href, REL_SELF);
    }

    /**
     * Creates a new {@link ExtendedLink} to the given URI with the given rel.
     *
     * @param href must not be {@literal null} or empty.
     * @param rel must not be {@literal null} or empty.
     */
    public ExtendedLink(final String href, final String rel) {
        super(new UriTemplate(href), rel);
    }

    /**
     * Returns a {@link ExtendedLink} pointing to the same URI but with the given relation.
     *
     * @param rel must not be {@literal null} or empty.
     * @return
     */
    public ExtendedLink withRel(final String rel) {
        return new ExtendedLink(getHref(), rel);
    }

    public ExtendedLink withMethods(final String... methods) {
        this.methods = methods;
        return this;
    }

    public ExtendedLink withDescription(final String description) {
        this.description = description;
        return this;
    }

    public ExtendedLink withName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getMethods() {
        return methods;
    }
}