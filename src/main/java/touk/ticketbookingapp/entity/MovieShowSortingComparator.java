package touk.ticketbookingapp.entity;

import java.util.Calendar;
import java.util.Comparator;

public class MovieShowSortingComparator implements Comparator<MovieShow> {
    @Override
    public int compare(MovieShow firstShow, MovieShow secondShow) {
        String firstMovieTitle = firstShow.getMovie().getTittle();
        String secondMovieTitle = secondShow.getMovie().getTittle();
        Calendar startOfFirstShow = firstShow.getStartAsCalendar();
        Calendar startOfSecondShow = secondShow.getStartAsCalendar();

        int compareMovieTitle = firstMovieTitle.compareTo(secondMovieTitle);
        int compareStartOfShow = startOfFirstShow.compareTo(startOfSecondShow);

        if (compareMovieTitle == 0)
            return compareStartOfShow;
        else
            return compareMovieTitle;
    }

}
