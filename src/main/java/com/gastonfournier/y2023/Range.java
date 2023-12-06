package com.gastonfournier.y2023;

import java.util.Optional;

record Range(long from, long to) {
  public boolean contains(long i) {
    return this.from <= i && i <= this.to;
  }

  public Optional<Range> intersection(Range other) {
    if (this.overlaps(other)) {
      return Optional.of(new Range(Math.max(this.from, other.from), Math.min(this.to, other.to)));
    } else {
      return Optional.empty();
    }
  }
  public long length() {
    return this.to - this.from + 1;
  }

  public boolean overlaps(Range other) {
    return this.from <= other.from && other.from <= this.to
            || this.from <= other.to && other.to <= this.to;
  }
}
