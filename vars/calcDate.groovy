import groovy.time.TimeCategory

class CalcDateParams {
    def base = new Date()
    int days = 0
    int hours = 0
    int minutes = 0
}

@NonCPS
def min(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base - params.days.days - params.hours.hours - params.minutes.minutes
    }

    return date
}

@NonCPS
def max(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base + params.days.days + params.hours.hours + params.minutes.minutes
    }

    return date
}