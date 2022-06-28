package com.item.web.comment;

import com.item.domain.comment.Comment;
import com.item.domain.comment.CommentRepository;
import com.item.domain.item.Item;
import com.item.domain.item.ItemRepository;
import com.item.domain.user.User;
import com.item.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment save(Long itemId, CommentSaveForm saveForm, String username) {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Not Fount itemId=" + itemId));

        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Fount username=" + username));

        Comment comment = saveForm.toEntity(findUser, findItem);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Transactional(readOnly = true)
    public List<Comment> findByItem(Item item) {
        return commentRepository.findByItem(item);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public void update(Long commentId, CommentUpdateForm updateForm) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Not Fount commentId=" + commentId));

        User user = findComment.getUser();
        Item item = findComment.getItem();

        findComment.update(updateForm.toEntity(user, item));
    }

    @Transactional
    public void delete(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Not Fount commentId=" + commentId));

        commentRepository.delete(findComment);
    }
}
