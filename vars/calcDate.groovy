import groovy.time.TimeCategory

@NonCPS
def min(def base = new Date(), def d, def h, def m) {
    def date = ''

    use (TimeCategory) {
        date = base - d.days - h.hours - m.minutes
    }

    return date
}

@NonCPS
def max(def base = new Date(), def d, def h, def m) {
    def date = ''

    use (TimeCategory) {
        date = base + d.days + h.hours + m.minutes
    }

    return date
}