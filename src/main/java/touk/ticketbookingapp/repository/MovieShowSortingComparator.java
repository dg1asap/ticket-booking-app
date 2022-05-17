package touk.ticketbookingapp.repository;

import touk.ticketbookingapp.entity.MovieShow;

import java.time.LocalDateTime;
import java.util.Comparator;

public class MovieShowSortingComparator implements Comparator<MovieShow> {
    @Override
    public int compare(MovieShow firstShow, MovieShow secondShow) {
        String firstMovieTitle = firstShow.getMovie().getTittle();
        String secondMovieTitle = secondShow.getMovie().getTittle();
        LocalDateTime startOfFirstShow = firstShow.getStartAsLocalDateTime();
        LocalDateTime startOfSecondShow = secondShow.getStartAsLocalDateTime();

        int compareMovieTitle = firstMovieTitle.compareTo(secondMovieTitle);
        int compareStartOfShow = startOfFirstShow.compareTo(startOfSecondShow);

        if (compareMovieTitle == 0)
            return compareStartOfShow;
        else
            return compareMovieTitle;
    }

}
